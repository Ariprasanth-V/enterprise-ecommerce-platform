import React from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, ShieldCheck, Truck, Clock, Headphones } from 'lucide-react';

export default function HomePage() {
    const categories = [
        { name: 'Electronics & Gadgets', image: 'https://images.unsplash.com/photo-1526738549149-8e07eca6c147?auto=format&fit=crop&q=80&w=500' },
        { name: 'Home & Office', image: 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&q=80&w=500' },
        { name: 'Apparel & Fashion', image: 'https://images.unsplash.com/photo-1441986300917-64674bd600d8?auto=format&fit=crop&q=80&w=500' },
        { name: 'Books & More', image: 'https://images.unsplash.com/photo-1495640388908-05fa85288e61?auto=format&fit=crop&q=80&w=500' },
    ];

    return (
        <div className="space-y-12">
            {/* Retail Hero Banner */}
            <div className="relative bg-slate-900 text-white rounded-2xl shadow-xl overflow-hidden py-20 px-8 md:px-16 flex flex-col items-start justify-center">
                <div className="absolute inset-0 opacity-20 bg-cover bg-center" style={{ backgroundImage: `url('https://images.unsplash.com/photo-1607082348824-0a96f2a4b7da?auto=format&fit=crop&q=80&w=1200')` }}></div>
                <div className="relative z-10 max-w-xl space-y-4">
                    <span className="bg-amber-500 text-slate-950 text-xs font-bold px-3 py-1 rounded-full uppercase tracking-wider">Holiday Deals</span>
                    <h1 className="text-4xl md:text-5xl font-extrabold tracking-tight">Find Everything You Need, Delivered Fast.</h1>
                    <p className="text-slate-300 text-base md:text-lg">Explore top-rated products across electronics, home decor, apparel, and daily essentials with secure checkout.</p>
                    <Link
                        to="/products"
                        className="inline-flex items-center gap-2 bg-amber-500 hover:bg-amber-400 text-slate-950 font-bold px-8 py-3.5 rounded-xl transition shadow-lg mt-2"
                    >
                        Shop All Products <ArrowRight className="w-5 h-5" />
                    </Link>
                </div>
            </div>

            {/* Categories Grid */}
            <div>
                <h2 className="text-2xl font-bold mb-6 text-slate-900">Shop by Department</h2>
                <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
                    {categories.map((cat, idx) => (
                        <Link key={idx} to="/products" className="group bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden hover:shadow-md transition">
                            <div className="h-40 overflow-hidden bg-slate-100">
                                <img src={cat.image} alt={cat.name} className="w-full h-full object-cover group-hover:scale-105 transition duration-300" />
                            </div>
                            <div className="p-4 font-semibold text-slate-800">{cat.name}</div>
                        </Link>
                    ))}
                </div>
            </div>

            {/* Trust Badges Bar */}
            <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6 bg-white p-6 rounded-xl border border-slate-200 shadow-sm">
                <div className="flex items-center gap-4">
                    <Truck className="w-8 h-8 text-indigo-600 flex-shrink-0" />
                    <div>
                        <h4 className="font-bold text-sm">Free Express Shipping</h4>
                        <p className="text-slate-500 text-xs">On orders over $50</p>
                    </div>
                </div>
                <div className="flex items-center gap-4">
                    <ShieldCheck className="w-8 h-8 text-indigo-600 flex-shrink-0" />
                    <div>
                        <h4 className="font-bold text-sm">Secure Payments</h4>
                        <p className="text-slate-500 text-xs">100% protected transactions</p>
                    </div>
                </div>
                <div className="flex items-center gap-4">
                    <Clock className="w-8 h-8 text-indigo-600 flex-shrink-0" />
                    <div>
                        <h4 className="font-bold text-sm">24/7 Support</h4>
                        <p className="text-slate-500 text-xs">Dedicated customer service</p>
                    </div>
                </div>
                <div className="flex items-center gap-4">
                    <Headphones className="w-8 h-8 text-indigo-600 flex-shrink-0" />
                    <div>
                        <h4 className="font-bold text-sm">Easy Returns</h4>
                        <p className="text-slate-500 text-xs">30-day hassle-free returns</p>
                    </div>
                </div>
            </div>
        </div>
    );
}