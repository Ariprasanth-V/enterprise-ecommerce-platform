import React, { useState, useEffect } from 'react';
import axiosClient from '../api/axiosClient';
import { Search, Loader2, X, ExternalLink } from 'lucide-react';

export default function ProductSearch() {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        const handleSearch = async () => {
            if (!query.trim()) {
                setResults([]);
                setIsOpen(false);
                return;
            }

            setLoading(true);
            setIsOpen(true);

            try {
                // Calls the search-service securely through the API Gateway endpoint (/api/v1/search)
                const response = await axiosClient.get(`/search/products?keyword=${encodeURIComponent(query)}`);
                setResults(response.data || []);
            } catch (err) {
                console.error('Elasticsearch search error:', err);
                setResults([]);
            } finally {
                setLoading(false);
            }
        };

        // Debounce search queries to optimize backend load
        const timer = setTimeout(handleSearch, 300);
        return () => clearTimeout(timer);
    }, [query]);

    return (
        <div className="relative w-full max-w-md mx-auto">
            {/* Search Input Bar */}
            <div className="relative flex items-center">
                <Search className="absolute left-3 w-4 h-4 text-slate-400" />
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Search products via Elasticsearch..."
                    className="w-full pl-9 pr-10 py-2 bg-slate-100 border border-slate-300 rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white transition"
                />
                {query && (
                    <button
                        onClick={() => { setQuery(''); setResults([]); setIsOpen(false); }}
                        className="absolute right-3 text-slate-400 hover:text-slate-600"
                    >
                        <X className="w-4 h-4" />
                    </button>
                )}
            </div>

            {/* Instant Search Results Dropdown */}
            {isOpen && (
                <div className="absolute top-full left-0 right-0 mt-2 bg-white rounded-2xl shadow-2xl border border-slate-200 overflow-hidden z-50 max-h-96 overflow-y-auto">
                    {loading && (
                        <div className="p-4 flex items-center justify-center gap-2 text-slate-500 text-sm">
                            <Loader2 className="w-4 h-4 animate-spin text-indigo-600" />
                            Searching index clusters...
                        </div>
                    )}

                    {!loading && results.length === 0 && (
                        <div className="p-4 text-center text-slate-500 text-sm">
                            No products found matching "{query}"
                        </div>
                    )}

                    {!loading && results.length > 0 && (
                        <div className="divide-y divide-slate-100">
                            {results.map((product) => (
                                <div
                                    key={product.id || product.sku}
                                    onClick={() => {
                                        setIsOpen(false);
                                        // Navigate or handle product click
                                    }}
                                    className="p-3 hover:bg-slate-50 flex items-center justify-between cursor-pointer transition"
                                >
                                    <div className="flex items-center gap-3">
                                        <div className="w-10 h-10 bg-slate-100 rounded-lg flex items-center justify-center font-bold text-xs text-indigo-600">
                                            {product.sku ? product.sku.substring(0, 3) : 'PRD'}
                                        </div>
                                        <div>
                                            <h4 className="font-semibold text-sm text-slate-800">{product.name || product.title}</h4>
                                            <p className="text-xs text-slate-500 line-clamp-1">{product.description}</p>
                                        </div>
                                    </div>
                                    <span className="font-bold text-sm text-indigo-600">${product.price}</span>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}