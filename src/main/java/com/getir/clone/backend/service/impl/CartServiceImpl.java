package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.request.CartItemRequest;
import com.getir.clone.backend.dto.response.CartDTO;
import com.getir.clone.backend.dto.response.CartItemDTO;
import com.getir.clone.backend.entity.Cart;
import com.getir.clone.backend.entity.CartItem;
import com.getir.clone.backend.entity.Product;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.exceptions.InsufficientStockException;
import com.getir.clone.backend.exceptions.NotFoundException;
import com.getir.clone.backend.repository.CartRepository;
import com.getir.clone.backend.service.interfaces.CartService;
import com.getir.clone.backend.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public CartDTO getCart(User user) {
        Cart cart = findOrCreateActiveCart(user);
        return toDto(cart);
    }

    @Override
    @Transactional
    public CartDTO addItem(User user, CartItemRequest request) {
        Cart cart = findOrCreateActiveCart(user);

        Product product = productService.getById(request.productId())
                .orElseThrow(NotFoundException::new);

        int totalRequested = request.quantity()
                + cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(request.productId()))
                .mapToInt(CartItem::getQuantity)
                .findFirst()
                .orElse(0);

        if (product.getStock() < totalRequested) {
            throw new InsufficientStockException();
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(request.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.quantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(request.quantity());
            cart.addItem(newItem);
        }

        productService.decreaseStock(product.getId(), request.quantity());

        Cart updatedCart = cartRepository.save(cart);
        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO updateItem(User user, CartItemRequest request) {
        Cart cart = findOrCreateActiveCart(user);

        CartItem cartItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(request.productId()))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        int currentQuantity = cartItem.getQuantity();
        int diff = request.quantity() - currentQuantity;

        Product product = productService.getById(request.productId())
                .orElseThrow(NotFoundException::new);

        if (diff > 0) {
            if (product.getStock() < diff) {
                throw new InsufficientStockException();
            }
            cartItem.setQuantity(request.quantity());
            productService.decreaseStock(product.getId(), diff);
        } else if (diff < 0) {
            cartItem.setQuantity(request.quantity());
            productService.increaseStock(product.getId(), -diff);
        }

        Cart updatedCart = cartRepository.save(cart);
        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO removeItem(User user, Long productId) {
        Cart cart = findOrCreateActiveCart(user);

        CartItem cartItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        productService.increaseStock(productId, cartItem.getQuantity());

        cart.removeItem(cartItem);

        Cart updatedCart = cartRepository.save(cart);
        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO checkout(User user) {
        Cart cart = findOrCreateActiveCart(user);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Sepet boş!");
        }

        cart.setCheckedOut(true);
        Cart updatedCart = cartRepository.save(cart);

        // TODO: Kafka, Mail, Log işlemlerini burada tetikle

        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        Cart cart = findOrCreateActiveCart(user);

        cart.getItems().forEach(item -> productService.increaseStock(item.getProduct().getId(), item.getQuantity()));
        cart.getItems().clear();
        cartRepository.save(cart);
    }


    private Cart findOrCreateActiveCart(User user) {
        return cartRepository.findByUserIdAndCheckedOutFalse(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    private CartDTO toDto(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream()
                .map(i -> new CartItemDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity()
                )).toList();
        return new CartDTO(cart.getId(), items, cart.isCheckedOut());
    }
}
