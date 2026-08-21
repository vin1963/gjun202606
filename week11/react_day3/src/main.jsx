import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'
//import App from './App.jsx'
//import AppMain from './AppMain.jsx'
//import ProductApp from './ProductProps.jsx'
//import ChildProps from './ChildProps.jsx'
//import Counter from './Counter.jsx'
import TodoList from './TodoList.jsx'
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <TodoList />
  </StrictMode>,
)
