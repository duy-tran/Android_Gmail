Chapter 9: Background Tasks
===========================

 

### Group 3

-   Tran Quang Duy - <duy9968@gmail.com>

-   Vu Binh Duong - <badboy.hp96@gmail.com>

-   Pham Tien Thanh - <vicnoob.gg@gmail.com>

 

### AsyncTask

An encapsulation of Handler and Thread

Can report its progress to UI

 

\`\`\`\`AsyncTask\<Param, Progress, Result\>\`\`\`\`

Params: parameters

Progress: to report progress of worker thread

Result: return value

 

\`\`\`\`doInBackground()\`\`\`\`: execute, compulsory function

Optional functions:

\`\`\`\`onPreExecute()\`\`\`\`: preparation

\`\`\`\`onProgressUpdate()\`\`\`\`: report progress

\`\`\`\`onPostExecute()\`\`\`\`: get the return value of task
