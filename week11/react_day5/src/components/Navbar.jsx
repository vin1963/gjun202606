import { Link, NavLink } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav>
      {/* Link：基本連結，不重整頁面 */}
      <Link to="/">首頁</Link> &nbsp;     
      <Link to="/home">主網頁</Link>  &nbsp;  
      <Link to="/products">商品列表</Link>&nbsp;
      <Link to="/about">關於我們</Link>
      <p></p>
        {/* NavLink：可設定「選取中」樣式 */}
    </nav>
  );
}