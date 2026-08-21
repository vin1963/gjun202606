// 寫法 A：函式宣告（Function Declaration）
function Welcome() {
  return <h1>歡迎來到 React 世界！</h1>;
}

// 寫法 B：箭頭函式（Arrow Function）
const WelcomeArrow = () => <h1>Arrow Function 歡迎來到 React 世界！</h1>;

// 兩種寫法都可以；注意變數名稱不能重複宣告，所以箭頭版用不同名稱

// 在其他元件中使用（像 HTML 標籤一樣，可以重複使用）
function AppMain() {
  return (
    <div>
      <Welcome />        {/* 寫法 A */}
      <WelcomeArrow />   {/* 寫法 B */}
      <WelcomeArrow />   {/* 寫法 B */}
      <Welcome />        {/* 寫法 A */}
    </div>
  );
}

export default AppMain;