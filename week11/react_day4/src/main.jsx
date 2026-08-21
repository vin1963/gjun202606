import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
//import App from './App.jsx'
//import LoginForm from './LoginForm.jsx'
//import UserList from './UserList.jsx'
//import Timer from './Timer.jsx'
//import TimeString from './TimeString.jsx'
//import UserSearch from './UserSearch.jsx'
import FakeProducts from './FakeProducts.jsx'
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <FakeProducts />
  </StrictMode>,
)
