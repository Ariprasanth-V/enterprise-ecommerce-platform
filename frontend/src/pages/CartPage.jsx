import React from 'react';
import { useCartStore } from '../store/useCartStore';
import { Trash2, ShoppingBag } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function CartPage() {
    const { cartItems, removeFromCart, clearCart } = useCartStore();

    const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

    if (cartItems.length === 0) {
        return (
            <div className="text-center py-24 space-y-4">
                <ShoppingBag className="w-16 h-16 mx-auto text-slate-300" />
                <h2 className="text-2xl font-bold">Your cart is empty</h2>
                <p className="text-slate-500">Explore our catalog and add items to your cart.</p>
                <Link to="/products" className="inline-block bg-indigo-600 text-white px-6 py-2.5 rounded-lg font-medium hover:bg-indigo-700 transition">
                    Browse Products
                </Link>
            </div>
        );
    }

    return (
        <div className="max-w-4xl mx-auto space-y-8">
            <h1 className="text-3xl font-bold tracking-tight">Shopping Cart</h1>
            <div className="bg-white shadow rounded-xl border border-slate-100 divide-y divide-slate-100">
                {cartItems.map((item) => (
                    <div key={item.id} className="p-6 flex items-center justify-between">
                        <div>
                            <h3 className="text-lg font-semibold">{item.name}</h3>
                            <p className="text-slate-500 text-sm">Qty: {item.quantity}</p>
                        </div>
                        <div className="flex items-center gap-6">
                            <span className="text-lg font-bold">${(item.price * item.quantity).toFixed(2)}</span>
                            <button
                                onClick={() => removeFromCart(item.id)}
                                className="text-rose-500 hover:text-rose-700 p-2 transition"
                            >
                                <Trash2 className="w-5 h-5" />
                            </button>
                        </div>
                    </div>
                ))}
            </div>
            <div className="bg-white p-6 rounded-xl shadow border border-slate-100 flex justify-between items-center">
                <div>
                    <span className="text-slate-500 text-sm">Total Amount:</span>
                    <h2 className="text-3xl font-extrabold text-indigo-600">${totalPrice.toFixed(2)}</h2>
                </div>
                <div className="flex gap-4">
                    <button onClick={clearCart} className="bg-slate-200 hover:bg-slate-300 text-slate-700 px-4 py-2 rounded-lg font-medium transition">
                        Clear Cart
                    </button>
                    <button onClick={() => alert('Proceeding to checkout via payment-service!')} className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded-lg font-medium transition">
                        Checkout
                    </button>
                </div>
            </div>
        </div>
    );
}