package com.zenden.sports_store.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Cart;
import com.zenden.sports_store.Classes.CartItem;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.DTO.CartCreateDTO;
import com.zenden.sports_store.Classes.DTO.CartReadDTO;
import com.zenden.sports_store.Mapper.CartMapper;
import com.zenden.sports_store.Repositories.CartItemRepository;
import com.zenden.sports_store.Repositories.CartRepository;
import com.zenden.sports_store.Repositories.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    public CartReadDTO read(Long id) {
        try {
            return cartMapper.cartToCartReadDTO(cartRepository.findById(id).get());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error reading cart" + id);
        }
    }

    public void create(CartCreateDTO cartCreateDTO) {
        Cart cart = cartMapper.cartCreateDTOtoCart(cartCreateDTO);
        cart.setTotalPrice(0.0);
        cart.setCartItems(null);
        cartRepository.save(cart);
    }

    public CartReadDTO getByUser(Long id) {
        return cartMapper.cartToCartReadDTO(cartRepository.findByUserId(id).get());
    }

    public CartReadDTO addProduct(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).get();
        Product product = productRepository.findById(productId).get();
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findAny();
        if (optionalCartItem.isPresent()) {
            optionalCartItem.get().setQuantity(optionalCartItem.get().getQuantity() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
            cartItemRepository.save(optionalCartItem.get());
        } else {
            cart.getCartItems().add(new CartItem(1, cart, product));
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
            cartRepository.save(cart);
        }

        return cartMapper.cartToCartReadDTO(cart);
    }

    public CartReadDTO removeProduct(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findAny();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getQuantity() <= 1) {
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemRepository.save(cartItem);
            }

            cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice());
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Product not found in the cart");
        }
        return cartMapper.cartToCartReadDTO(cart);
    }

}
