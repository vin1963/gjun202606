// 子元件：接收 props
import React from 'react';

function ProductCard({ name, price, inStock }) {
  return (
    <div className="Card">      
      <h2>{name}</h2>
      <p>價格：${price}</p>
      {inStock ? <span>有庫存</span> : <span>缺貨中</span>}
    </div>
  );
}

// 父元件：傳遞 props（像 HTML 屬性一樣）
function ProductApp() {
  return (
    <div>
      <ProductCard name="iPhone 16" price={999} inStock={true} />
      <ProductCard name="AirPods Pro" price={249} inStock={false} />
    </div>
  );
}

export default ProductApp;