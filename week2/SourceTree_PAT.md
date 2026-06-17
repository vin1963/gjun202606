## 問題分析

這是很常見的 **SourceTree + GitHub PAT 驗證問題**。原因通常是：

1. SourceTree 或 Git 仍然**緩存了舊的帳號密碼**（而非 PAT）
2. 即使你輸入了正確的 PAT，系統仍會用舊的快取憑證去驗證，導致「帳號密碼不正確」

---

## 解決方案

### 1️⃣ 清除 Windows 認證管理員的快取
- 開啟 **控制台 → 使用者帳戶 → 認證管理員 → Windows 認證**
- 找到 `git:https://github.com` 或名稱包含 GitHub 的**一般認證**
- **刪除**這些舊憑證

### 2️⃣ 清除 SourceTree 內嵌憑證
- SourceTree → **工具 → 選項 → 驗證**
- 找到 GitHub → **重新整理 OAuth** 或 **移除** 已儲存的帳號

### 3️⃣ 使用命令列清除（PowerShell）
```powershell
# 清除 GitHub 的 Git 憑證快取
git credential-manager reject https://github.com

# 或查看/刪除 Windows 儲存的目標
cmdkey /list | findstr github
cmdkey /delete:<目標名稱>
```

### 4️⃣ 重新進行 push 驗證
清除後再次 Push，SourceTree 會跳出憑證視窗：
- **使用者名稱**：你的 GitHub 使用者名稱（或 email）
- **密碼**：**PAT 權杖**（例如 `ghp_xxxxxxxxxxxx`）— **不是 GitHub 密碼**

---

### ⚠️ 使用 PAT 的重要提醒
- PAT 必須開啟 **`repo` 權限範圍**（如果是私人仓库）
- 使用者名稱欄位**不要填** "token" — 填你的 GitHub 帳號即可
- PAT 產生後建議先到 GitHub 確認權限是否正確

需要我對某個步驟進一步說明嗎？
好的，以下是各步驟的詳細說明：

---

## 步驟 1 — 清除 Windows 認證管理員

這是最常見的原因 — SourceTree 或 Git 之前儲存了舊的 GitHub 密碼（或已失效的 PAT），即使你手動輸入新的 PAT，系統仍會自動送出快取的舊憑證。

**操作路徑：**
1. 按 `Win + R` → 輸入 `control` → 開啟控制台
2. 檢視方式改為**大圖示** → 點選**認證管理員**
3. 點選 **Windows 認證**
4. 在「一般認證」列表中找以下名稱的項目：
   - `git:https://github.com`
   - `GitHub` 開頭的項目
   - 任何 `adaam*` / `github.com` 相關的項目
5. 點開 → **移除**

> 如果找不到，按下方的「其他認證」也可能有。全部刪掉沒關係，下次 push 時會重新問你。

---

## 步驟 2 — 清除 SourceTree 內建憑證

SourceTree 自己也有存一組憑證，和 Windows 認證管理員是分開的。

**操作路徑：**
1. SourceTree 選單 → **工具 → 選項**
2. 切到 **認證** 頁籤
3. 找到 GitHub 的項目 → 點 **編輯** → 直接**刪除**
   - 或點 **重新整理 OAuth**（如果你之前是用 OAuth 授權）

> 如果你看到多筆 GitHub 的記錄，全部刪掉。

---

## 步驟 3 — 命令列清除（進階，但最徹底）

如果以上做完還是不行，可能是 Git 自己的 credential helper 也存了。

**用 PowerShell（系統管理員權限）執行：**

```powershell
# 方法 A：拒絕 GitHub 憑證（要求 Git 下次重新索取）
git credential-manager reject https://github.com

# 方法 B：如果上面沒用，直接刪 Windows 認證
cmdkey /list                      # 列出所有已儲存的目標
cmdkey /delete:git:https://github.com
```

> 如果 `cmdkey /list` 找不到目標，名稱可能是 `GitHub` 或其他格式，自行取代即可。

---

## 步驟 4 — 重新 Push + 正確輸入 PAT

清除完上述所有快取後，回到 SourceTree 按 **Push**。

這時會跳出 SourceTree 的認證視窗（不是瀏覽器）：

| 欄位 | 正確填法 |
|------|---------|
| **使用者名稱** | 你的 GitHub **使用者名稱**（例如 `adaam`），也可以用註冊 email |
| **密碼** | 貼上 **PAT**（字串以 `github_pat_` 或 `ghp_` 開頭） |

❌ **常見錯誤：**
- 使用者名稱填 `token` → **錯的**，使用者名稱請填你的帳號
- 密碼填原本的 GitHub 登入密碼 → **錯的**，PAT 啟用後 GitHub 密碼不能用來 push

---

## 步驟 5 — 驗證 PAT 權限

如果還是失敗，請到 GitHub 檢查你的 PAT 權限：

1. GitHub → Settings → **Developer settings** → **Personal access tokens** → **Tokens (classic)**
2. 看該 PAT 的 **Scopes** 有沒有勾：
   - ✅ `repo`（完整控制私人仓库）— **必要**
   - ✅ `workflow`（如果你要 push 到有 GitHub Actions 的 repo）
3. 如果 PAT 已過期或權限不足，**重新產生一個新的**

---

## 如果仍然不行

1. **重開機** — 有時候 credential helper 的服務（git-credential-manager）需要重啟
2. 確認 SourceTree 版本 → 很舊的版本對 PAT 支援較差，建議更新
3. 改用 **Git Bash / PowerShell 命令列** 直接 push 測試看看，排除 SourceTree 本身的問題：
   ```powershell
   git push origin master
   ```
   第一次 push 會問帳密，照上述方式輸入 PAT，然後在 SourceTree 也做一次

需要我對哪一段再深入說明嗎？
