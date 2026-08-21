import { useState } from 'react';

function Counter() {
  // useState(初始值) 回傳 [目前值, 更新函式]
  const [count, setCount] = useState(1);

  return (
    <div>
      <p>計數：{count}</p>
      <button onClick={() => setCount( c => c + 1)}>+1</button>
      <button onClick={() => setCount( c =>  c - 1)}>-1</button>
      <button onClick={() => setCount(0)}>重置</button>
    </div>
  );
}

export default Counter;