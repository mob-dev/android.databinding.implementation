public interface RetrofitAPIInterface {
    @GET("todos/1")
    Observable<APIResponseObject> makeObservableQuery();

    @GET("todos/2")
    Flowable<APIResponseObject> makeQuery();
}