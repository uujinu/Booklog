import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import './index.css';
import App from './App';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePickerUtils } from 'components/common/calendar';

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
      <LocalizationProvider
        dateAdapter={AdapterDayjs}
        dateFormats={DatePickerUtils}
      >
        <App />
      </LocalizationProvider>
    </BrowserRouter>
  </QueryClientProvider>
);
