import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/User/Login';
import BookList from 'components/book/BookList';
import BookDetails from 'components/book/BookDetails';
import { MyPage } from './pages/User/MyPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />}></Route>
      <Route path="book" element={<BookList />}></Route>
      <Route path="/book/:id" element={<BookDetails />}></Route>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/my-page" element={<MyPage />}></Route>
    </Routes>
  );
}

export default App;
