### What is this repository for? ###

* Data binding implementation
* Use MVVM architecture, Retrofit and how to use Executors implementation in android application.
* Use repository structure
* Use `Future` util component
* Use `fromPublisher` to convert LiveData objects to reactive streams, or from reactive streams to LiveData objects.

### Dependencies

* Lifecycle
* Retrofit2
* Rxjava2
* RxAndroid

### How do I get set up? ###

* Add dependencies of `build.gradle` file in app/build.gradle.
* Add permission in AndroidManifest.xml
```
<uses-permission android:name="android.permission.INTERNET"/>
```
* Create pojo `APIResponseObject` class.
* Create interface `RetrofitAPIInterface` used for api calling through `Retrofit`.
* Create `ServiceGenerator` intiating retrofit. `LiveData` observable is used with `Retrofit`.
* Create `ExampleViewModelRepository` class that is used by view model. This is a part of `MVVM` implementation and responsible to communicate with server and provide data to view model(client).
* Create view model `ExampleViewModel` which contains `MutableLiveData` params and acts like a bridge between `View(Fragment/Activity)` and `Model(APIResponseObject)/Repository`.
* Create layout class `fragment_example.xml` that contains a `TextView` and a `EditText` and reference of view model.
* Create `ExampleFragment` fragment and see the code for data binding in `onCreateView` method.
* See method `setValueGettingViewIdFromBindingObject` to set value in a view.
* Create `MainActivity` activity contains code "Use of LiveData" and "Use of Future keyword". `Future` works as `Promise`.