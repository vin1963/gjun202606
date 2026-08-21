import { useState } from 'react';

// ===== 物件 State =====
function ProfileForm() {
  const [user, setUser] = useState({ name: "", email: "" });

  const handleNameChange = (e) => {
    // ✅ 用展開運算子保留其他欄位，只更新需要的
    setUser({ ...user, name: e.target.value });
  };

  return (
    <input value={user.name} onChange={handleNameChange} />
  );
}

// ===== 陣列 State（每筆資料帶唯一 id，讓 key 有穩定值）=====
function TodoList() {
  const [todos, setTodos] = useState([
    { id: 1, text: "買咖啡" },
    { id: 2, text: "學 React" },
  ]);

  // 新增（展開運算子產生新陣列）
  const addTodo = (text) => {
    setTodos([...todos, { id: Date.now(), text }]); // ✅
  };

  // 刪除（用 filter 產生新陣列）
  const removeTodo = (id) => {
    setTodos(todos.filter((todo) => todo.id !== id)); // ✅
  };

  // 更新（用 map 產生新陣列）
  const updateTodo = (id, newText) => {
    setTodos(
      todos.map((todo) => (todo.id === id ? { ...todo, text: newText } : todo)) // ✅
    );
  };

  return (
    <div className="container">       
    <h1>待辦清單</h1>
    <button onClick={() => addTodo(prompt("請輸入新任務"))}>新增任務</button>
    <ul>
      {todos.map((todo) => (
        <li key={todo.id}>
          {todo.text}
          <button onClick={() => removeTodo(todo.id)}>刪除</button>
        </li>
      ))}
    </ul>
    </div>
  );
}

export default TodoList;