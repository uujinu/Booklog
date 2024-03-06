import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 0
    }
  }
});

root.render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </QueryClientProvider>
);
