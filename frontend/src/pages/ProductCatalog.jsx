import React, { useEffect, useState } from 'react';
import axiosClient from '../api/axiosClient';
import { useCartStore } from '../store/useCartStore';
import { ShoppingCart, Search } from 'lucide-react';

export default function ProductCatalog() {
    const [products, setProducts] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const addToCart = useCartStore((state) => state.addToCart);

    useEffect(() => {
        // Fetch products from API Gateway -> product-service
        axiosClient.get('/products')
            .then((res) => {
                setProducts(res.data);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching products:", err);
                // Fallback mock data if backend isn't seeded yet
                setProducts([
                    { id: 1, name: 'Wireless Mechanical Keyboard', price: 99.99, description: 'RGB backlit mechanical keyboard with blue switches.' },
                    { id: 2, name: 'Ergonomic Office Chair', price: 249.99, description: 'Adjustable lumbar support breathable mesh chair.' },
                    { id: 3, name: 'UltraWide 4K Monitor', price: 499.99, description: '34-inch IPS display with USB-C power delivery.' },
                ]);
                setLoading(false);
            });
    }, []);

    const filteredProducts = products.filter(p =>
        p.name.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="space-y-8">
            <div className="flex flex-col md:flex-row justify-between items-center gap-4">
                <h1 className="text-3xl font-bold tracking-tight">Product Catalog</h1>
                <div className="relative w-full md:w-80">
                    <Search className="absolute left-3 top-3 w-5 h-5 text-slate-400" />
                    <input
                        type="text"
                        placeholder="Search products..."
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        className="w-full pl-10 pr-4 py-2 bg-white border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    />
                </div>
            </div>

            {loading ? (
                <div className="text-center py-20 text-slate-500">Loading products...</div>
            ) : (
                <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-6">
                    {filteredProducts.map((product) => (
                        <div key={product.id} className="bg-white p-6 rounded-xl shadow border border-slate-100 flex flex-col justify-between">
                            <div>
                                <h3 className="text-xl font-semibold mb-2">{product.name}</h3>
                                <p className="text-slate-600 text-sm mb-4">{product.description}</p>
                            </div>
                            <div className="flex items-center justify-between mt-4">
                                <span className="text-2xl font-bold text-indigo-600">${product.price.toFixed(2)}</span>
                                <button
                                    onClick={() => addToCart(product)}
                                    className="flex items-center gap-2 bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition"
                                >
                                    <ShoppingCart className="w-4 h-4" /> Add to Cart
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}