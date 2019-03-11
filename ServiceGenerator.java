public class ServiceGenerator {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RetrofitAPIInterface retrofitAPIInterface = retrofit.create(RetrofitAPIInterface.class);

    public static RetrofitAPIInterface getRetrofitAPIInterface(){
        return retrofitAPIInterface;
    }
}