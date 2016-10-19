# MyAsyncTask
用AsyncTask从网络加载图片，存入LruCache中
AsyncTask，总结起来就是: 3个泛型，4个步骤

AsyncTask　<Params, Progress, Result>
1）	Params: 这个泛型指定的是我们传递给异步任务执行时的参数的类型。通常指网络下载数据的路径,为String类型
2）	Progress: 这个泛型指定的是我们的异步任务在执行的时候将执行的进度返回给UI线程的参数的类型。通常指进度的刻度，为Integer类型
3）	Result: 这个泛型指定的异步任务执行完后返回给UI线程的结果的类型

4个步骤：
步骤一：
        onPreExecute(): 这个方法是在执行异步任务之前的时候执行，并且是在UI Thread当中执行的，通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出要给用户提示的ProgressDialog。
步骤二：
       doInBackground(Params... params): 在onPreExecute()方法执行完之后，会马上执行这个方法，这个方法就是来处理异步任务的方法，Android操作系统会在后台的线程池当中开启一个worker thread(子线程)来执行我们的这个方法，所以这个方法是在worker thread当中执行的，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作。这个方法执行完之后就可以将我们的执行结果发送给我们的最后一个 onPostExecute 方法。注意：此方法不是 UI线程中执行。在这个步骤里可以使用publishProgress这个方法 用来更新进度的刻度，那这些刻度的值会发布在UI的主线程上，通常发布来说也是和progressbar来结合使用的
步骤三：
onProgressUpdate(Progess... values): 在第二步里执行publishProgress这个方法后，UI线程就会去执行这个方法 ，这个方法是用来更新UI的进度条的
步骤四：
        onPostExecute(Result... result): 当我们的异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI Thread当中调用的，我们可以将返回的结果显示在UI控件上，用来发布后台执行完的结果的

总结：以上4个方法中doInBackground是在work Thread线程中执行，其余3个方法均是在UI(main)线程中执行。
为什么我们的AsyncTask抽象类只有一个 doInBackground 的抽象方法呢？？原因是，我们如果要做一个异步任务，我们必须要为其开辟一个新的Thread，让其完成一些操作，而在完成这个异步任务时，我可能并不需要弹出要给用户提示的ProgressDialog，我们可能并不需要随时更新我的ProgressDialog的进度条，我也可能并不需要将结果更新给我们的UI界面，所以除了 doInBackground 方法之外的三个方法，都不是必须有的，因此我们必须要实现的方法是 doInBackground 方法。



