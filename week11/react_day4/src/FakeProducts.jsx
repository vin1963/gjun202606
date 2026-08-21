import { useState, useEffect } from 'react';

function FakeProducts() {
  const [products, setProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    fetch('https://fakestoreapi.com/products')
      .then(res => res.json())
      .then(data => {
        setProducts(data);
        setLoading(false);
      });
  }, []);

  // 根據 searchTerm 過濾產品（不需要再呼叫 API）
  const filteredProducts = products.filter(product =>
    product.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <input
        type="text"
        placeholder="搜尋產品..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {loading ? (
        <p>載入中...</p>
      ) : (
        <table border="1" width="80%">
          {filteredProducts.map(product => (
            <tr key={product.id}>
              <td><strong>{product.title}</strong></td>
              <td>${product.price}</td>
            </tr>
          ))}
          {filteredProducts.length === 0 && (
            <tr>
              <td colSpan="2">找不到符合的產品</td>
            </tr>
          )}
        </table>
      )}
    </div>
  );
}

export default FakeProducts;