import React from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, ShieldCheck, Zap, Cpu } from 'lucide-react';

export default function HomePage() {
    return (
        <div className="space-y-16">
            <div className="text-center py-20 bg-gradient-to-r from-indigo-900 via-slate-900 to-slate-900 text-white rounded-2xl shadow-xl px-6">
                <h1 className="text-5xl font-extrabold mb-6 tracking-tight">Next-Gen Enterprise E-Commerce</h1>
                <p className="text-xl text-slate-300 max-w-2xl mx-auto mb-8">
                    Powered by a robust 16-module microservices backend, real-time event streaming, and a high-performance React UI.
                </p>
                <Link
                    to="/products"
                    className="inline-flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-8 py-3.5 rounded-xl transition shadow-lg"
                >
                    Explore Catalog <ArrowRight className="w-5 h-5" />
                </Link>
            </div>

            <div className="grid md:grid-cols-3 gap-8">
                <div className="bg-white p-6 rounded-xl shadow border border-slate-100 flex flex-col items-center text-center">
                    <Zap className="w-12 h-12 text-indigo-600 mb-4" />
                    <h3 className="text-lg font-bold mb-2">Lightning Fast</h3>
                    <p className="text-slate-600 text-sm">Optimized with Redis caching and Elasticsearch for instant search results and responsive catalog browsing.</p>
                </div>
                <div className="bg-white p-6 rounded-xl shadow border border-slate-100 flex flex-col items-center text-center">
                    <ShieldCheck className="w-12 h-12 text-indigo-600 mb-4" />
                    <h3 className="text-lg font-bold mb-2">Secure & Reliable</h3>
                    <p className="text-slate-600 text-sm">Secured via Spring Cloud Gateway, JWT authentication, and isolated microservice boundaries.</p>
                </div>
                <div className="bg-white p-6 rounded-xl shadow border border-slate-100 flex flex-col items-center text-center">
                    <Cpu className="w-12 h-12 text-indigo-600 mb-4" />
                    <h3 className="text-lg font-bold mb-2">Event-Driven</h3>
                    <p className="text-slate-600 text-sm">Asynchronous order processing and inventory updates powered by Apache Kafka message brokers.</p>
                </div>
            </div>
        </div>
    );
}