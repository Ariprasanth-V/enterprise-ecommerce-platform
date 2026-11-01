import React, { useEffect, useState } from 'react';
import axiosClient from '../api/axiosClient';
import { Package } from 'lucide-react';

export default function OrdersPage() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axiosClient.get('/orders')
            .then((res) => {
                setOrders(res.data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                // Mock orders for testing UI
                setOrders([
                    { id: 'ORD-9821', date: '2026-10-31', total: 349.98, status: 'DELIVERED' },
                    { id: 'ORD-9822', date: '2026-11-01', total: 99.99, status: 'PROCESSING' },
                ]);
                setLoading(false);
            });
    }, []);

    return (
        <div className="max-w-4xl mx-auto space-y-6">
            <h1 className="text-3xl font-bold tracking-tight">Order History</h1>
            {loading ? (
                <div className="text-center py-20 text-slate-500">Loading orders...</div>
            ) : (
                <div className="space-y-4">
                    {orders.map((order) => (
                        <div key={order.id} className="bg-white p-6 rounded-xl shadow border border-slate-100 flex items-center justify-between">
                            <div className="flex items-center gap-4">
                                <div className="p-3 bg-indigo-50 text-indigo-600 rounded-lg">
                                    <Package className="w-6 h-6" />
                                </div>
                                <div>
                                    <h3 className="font-semibold text-lg">{order.id}</h3>
                                    <p className="text-slate-500 text-sm">Placed on {order.date}</p>
                                </div>
                            </div>
                            <div className="text-right">
                                <span className="block font-bold text-lg">${order.total.toFixed(2)}</span>
                                <span className={`inline-block px-2.5 py-1 rounded-full text-xs font-semibold ${
                                    order.status === 'DELIVERED' ? 'bg-emerald-50 text-emerald-600' : 'bg-amber-50 text-amber-600'
                                }`}>
                  {order.status}
                </span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}