import React, { useState } from 'react';
import axiosClient from '../api/axiosClient';
import { Sparkles, Send, Bot, User, Loader2 } from 'lucide-react';

export default function AIAssistant() {
    const [isOpen, setIsOpen] = useState(false);
    const [prompt, setPrompt] = useState('');
    const [messages, setMessages] = useState([
        { role: 'assistant', text: 'Hello! I am your AI shopping assistant powered by Gemini. How can I help you find products today?' }
    ]);
    const [loading, setLoading] = useState(false);

    const handleSendMessage = async (e) => {
        e.preventDefault();
        if (!prompt.trim() || loading) return;

        const userMessage = prompt.trim();
        setPrompt('');
        setMessages((prev) => [...prev, { role: 'user', text: userMessage }]);
        setLoading(true);

        try {
            const response = await axiosClient.post('/ai/recommendations', {
                customerId: 101, // Pass active user ID from your auth store if available
                searchContext: userMessage
            });

// Extract the aiMessage returned by your service record
            const aiReply = response.data?.aiMessage || 'Here are some product recommendations for you!';
            setMessages((prev) => [...prev, { role: 'assistant', text: aiReply }]);
        } catch (err) {
            console.error('AI Service error:', err);
            // Fallback response for offline/local testing without active ai-service container
            setMessages((prev) => [
                ...prev,
                { role: 'assistant', text: "I'm currently having trouble connecting to the ai-service backend, but I can help you search our standard catalog!" }
            ]);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="fixed bottom-6 right-6 z-50">
            {/* Toggle Button */}
            {!isOpen && (
                <button
                    onClick={() => setIsOpen(true)}
                    className="bg-indigo-600 hover:bg-indigo-700 text-white p-4 rounded-full shadow-lg flex items-center gap-2 transition transform hover:scale-105"
                    title="Open AI Shopping Assistant"
                >
                    <Sparkles className="w-6 h-6 animate-pulse" />
                    <span className="font-semibold text-sm hidden sm:inline">AI Shopping Assistant</span>
                </button>
            )}

            {/* Chat Window */}
            {isOpen && (
                <div className="bg-white w-80 sm:w-96 h-[500px] rounded-2xl shadow-2xl border border-slate-200 flex flex-col overflow-hidden animate-in fade-in slide-in-from-bottom-4">
                    {/* Header */}
                    <div className="bg-indigo-600 text-white p-4 flex items-center justify-between">
                        <div className="flex items-center gap-2">
                            <Sparkles className="w-5 h-5" />
                            <h3 className="font-bold text-sm">Enterprise AI Assistant</h3>
                        </div>
                        <button
                            onClick={() => setIsOpen(false)}
                            className="text-indigo-200 hover:text-white text-lg font-bold px-2"
                        >
                            ×
                        </button>
                    </div>

                    {/* Messages Feed */}
                    <div className="flex-1 p-4 overflow-y-auto space-y-3 bg-slate-50">
                        {messages.map((msg, idx) => (
                            <div
                                key={idx}
                                className={`flex gap-2.5 ${msg.role === 'user' ? 'justify-end' : 'justify-start'}`}
                            >
                                {msg.role === 'assistant' && (
                                    <div className="w-7 h-7 rounded-full bg-indigo-100 text-indigo-600 flex items-center justify-center shrink-0">
                                        <Bot className="w-4 h-4" />
                                    </div>
                                )}
                                <div
                                    className={`max-w-[75%] p-3 rounded-xl text-sm leading-relaxed ${
                                        msg.role === 'user'
                                            ? 'bg-indigo-600 text-white rounded-br-none'
                                            : 'bg-white text-slate-800 border border-slate-200 shadow-sm rounded-bl-none'
                                    }`}
                                >
                                    {msg.text}
                                </div>
                                {msg.role === 'user' && (
                                    <div className="w-7 h-7 rounded-full bg-slate-200 text-slate-700 flex items-center justify-center shrink-0">
                                        <User className="w-4 h-4" />
                                    </div>
                                )}
                            </div>
                        ))}
                        {loading && (
                            <div className="flex gap-2.5 items-center text-slate-400 text-xs italic">
                                <Loader2 className="w-4 h-4 animate-spin text-indigo-600" />
                                AI is searching product vectors...
                            </div>
                        )}
                    </div>

                    {/* Input Footer */}
                    <form onSubmit={handleSendMessage} className="p-3 bg-white border-t border-slate-200 flex gap-2">
                        <input
                            type="text"
                            value={prompt}
                            onChange={(e) => setPrompt(e.target.value)}
                            placeholder="Ask about products, specs, deals..."
                            className="flex-1 px-3 py-2 border border-slate-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 focus:outline-none"
                        />
                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-indigo-600 hover:bg-indigo-700 text-white p-2 rounded-lg transition disabled:opacity-50 flex items-center justify-center"
                        >
                            <Send className="w-4 h-4" />
                        </button>
                    </form>
                </div>
            )}
        </div>
    );
}