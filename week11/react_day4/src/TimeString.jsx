import { useState, useEffect } from 'react';

function TimeString() {
  const [timeString, setTimeString] = useState(new Date().toLocaleTimeString());

  useEffect(() => {
    // 設定計時器
    const intervalId = setInterval(() => {
      setTimeString(new Date().toLocaleTimeString());
    }, 1000);

    // 清除函式：元件卸載時清除計時器，避免記憶體洩漏（Memory Leak）
    return () => {
      clearInterval(intervalId);
    };
  }, []); // 只設定一次

  return (<h3>現在時間：{timeString}</h3>);
}
export default TimeString;