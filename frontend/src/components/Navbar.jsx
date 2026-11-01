import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ShoppingCart, User, Package, LogOut, Store } from 'lucide-react';
import { useAuthStore } from '../store/useAuthStore';
import { useCartStore } from '../store/useCartStore';

export default function Navbar() {
    const { isAuthenticated, logout } = useAuthStore();
    const cartItems = useCartStore((state) => state.cartItems);
    const navigate = useNavigate();

    const totalItems = cartItems.reduce((sum, item) => sum + item.quantity, 0);

    return (
        <nav className="bg-slate-900 text-white shadow-md sticky top-0 z-50">
            <div className="container mx-auto px-4 py-4 flex justify-between items-center">
                <Link to="/" className="text-2xl font-bold flex items-center gap-2 text-indigo-400">
                    <Store className="w-7 h-7" /> EnterpriseStore
                </Link>
                <div className="flex items-center gap-6">
                    <Link to="/products" className="hover:text-indigo-300 transition">Products</Link>
                    <Link to="/cart" className="relative hover:text-indigo-300 transition flex items-center gap-1">
                        <ShoppingCart className="w-5 h-5" /> Cart
                        {totalItems > 0 && (
                            <span className="absolute -top-2 -right-3 bg-indigo-500 text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
                {totalItems}
              </span>
                        )}
                    </Link>
                    {isAuthenticated ? (
                        <>
                            <Link to="/orders" className="flex items-center gap-1 hover:text-indigo-300 transition">
                                <Package className="w-5 h-5" /> Orders
                            </Link>
                            <button
                                onClick={() => { logout(); navigate('/login'); }}
                                className="flex items-center gap-1 bg-rose-600 hover:bg-rose-700 px-3 py-1.5 rounded text-sm transition"
                            >
                                <LogOut className="w-4 h-4" /> Logout
                            </button>
                        </>
                    ) : (
                        <Link to="/login" className="flex items-center gap-1 bg-indigo-600 hover:bg-indigo-700 px-4 py-1.5 rounded text-sm transition">
                            <User className="w-4 h-4" /> Login
                        </Link>
                    )}
                </div>
            </div>
        </nav>
    );
}