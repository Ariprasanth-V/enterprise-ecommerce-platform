import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useCartStore = create(
    persist(
        (set, get) => ({
            cartItems: [],
            addToCart: (product) => {
                const items = get().cartItems;
                const existing = items.find((item) => item.id === product.id);
                if (existing) {
                    set({
                        cartItems: items.map((item) =>
                            item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
                        ),
                    });
                } else {
                    set({ cartItems: [...items, { ...product, quantity: 1 }] });
                }
            },
            removeFromCart: (productId) => {
                set({ cartItems: get().cartItems.filter((item) => item.id !== productId) });
            },
            clearCart: () => set({ cartItems: [] }),
        }),
        { name: 'cart-storage' }
    )
);