import React from 'react';
import { useCartStore } from '../store/useCartStore';
import { Trash2, ShoppingBag, Plus, Minus } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function CartPage() {
    const { cartItems, addToCart, removeFromCart, clearCart } = useCartStore();

    const decreaseQuantity = (product) => {
        if (product.quantity > 1) {
            // Decrease quantity by replacing or reducing
            useCartStore.setState({
                cartItems: cartItems.map((item) =>
                    item.id === product.id ? { ...item, quantity: item.quantity - 1 } : item
                ),
            });
        } else {
            removeFromCart(product.id);
        }
    };

    const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

    if (cartItems.length === 0) {
        return (
            <div className="text-center py-24 space-y-4">
                <ShoppingBag className="w-16 h-16 mx-auto text-slate-300" />
                <h2 className="text-2xl font-bold text-slate-900">Your cart is empty</h2>
                <p className="text-slate-500">Explore our catalog and add items to your cart.</p>
                <Link to="/products" className="inline-block bg-indigo-600 text-white px-6 py-2.5 rounded-lg font-medium hover:bg-indigo-700 transition">
                    Browse Products
                </Link>
            </div>
        );
    }

    return (
        <div className="max-w-4xl mx-auto space-y-8">
            <h1 className="text-3xl font-bold tracking-tight text-slate-900">Shopping Cart</h1>
            <div className="bg-white shadow-sm rounded-xl border border-slate-200 divide-y divide-slate-100">
                {cartItems.map((item) => (
                    <div key={item.id} className="p-6 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
                        <div>
                            <h3 className="text-lg font-semibold text-slate-900">{item.name}</h3>
                            <p className="text-indigo-600 font-medium text-sm">${item.price.toFixed(2)} each</p>
                        </div>
                        <div className="flex items-center gap-6 w-full sm:w-auto justify-between sm:justify-end">
                            {/* Quantity Controls */}
                            <div className="flex items-center border border-slate-300 rounded-lg overflow-hidden">
                                <button
                                    onClick={() => decreaseQuantity(item)}
                                    className="px-3 py-1 bg-slate-50 hover:bg-slate-100 text-slate-600 transition"
                                >
                                    <Minus className="w-4 h-4" />
                                </button>
                                <span className="px-4 py-1 text-sm font-semibold text-slate-800">{item.quantity}</span>
                                <button
                                    onClick={() => addToCart(item)}
                                    className="px-3 py-1 bg-slate-50 hover:bg-slate-100 text-slate-600 transition"
                                >
                                    <Plus className="w-4 h-4" />
                                </button>
                            </div>

                            <span className="text-lg font-bold text-slate-900 w-24 text-right">
                ${(item.price * item.quantity).toFixed(2)}
              </span>

                            <button
                                onClick={() => removeFromCart(item.id)}
                                className="text-rose-500 hover:text-rose-700 p-2 transition"
                                title="Remove item"
                            >
                                <Trash2 className="w-5 h-5" />
                            </button>
                        </div>
                    </div>
                ))}
            </div>
            <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col sm:flex-row justify-between items-center gap-4">
                <div>
                    <span className="text-slate-500 text-sm">Total Amount:</span>
                    <h2 className="text-3xl font-extrabold text-indigo-600">${totalPrice.toFixed(2)}</h2>
                </div>
                <div className="flex gap-4 w-full sm:w-auto">
                    <button onClick={clearCart} className="flex-1 sm:flex-none bg-slate-100 hover:bg-slate-200 text-slate-700 px-5 py-2.5 rounded-lg font-medium transition">
                        Clear Cart
                    </button>
                    <button onClick={() => alert('Proceeding to secure checkout via payment service!')} className="flex-1 sm:flex-none bg-amber-500 hover:bg-amber-400 text-slate-950 px-8 py-2.5 rounded-lg font-bold transition shadow-sm">
                        Proceed to Checkout
                    </button>
                </div>
            </div>
        </div>
    );
}