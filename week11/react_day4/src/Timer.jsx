import { useState, useEffect } from 'react';

function Timer() {
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    // 設定計時器
    const intervalId = setInterval(() => {
      setSeconds(prev => prev + 1);
    }, 1000);

    // 清除函式：元件卸載時清除計時器，避免記憶體洩漏（Memory Leak）
    return () => {
      clearInterval(intervalId);
    };
  }, []); // 只設定一次

  return <p>已計時：{seconds} 秒</p>;
}
export default Timer;