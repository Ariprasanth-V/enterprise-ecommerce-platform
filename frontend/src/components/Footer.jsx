import React from 'react';

export default function Footer() {
    return (
        <footer className="bg-slate-900 text-slate-400 py-6 border-t border-slate-800">
            <div className="container mx-auto px-4 text-center text-sm">
                <p>&copy; {new Date().getFullYear()} Enterprise E-Commerce Platform. Microservices Architecture Powered by Spring Boot & React.</p>
            </div>
        </footer>
    );
}