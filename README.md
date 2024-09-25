
# NewsApp

A simple news app built using **Kotlin** and **Jetpack Compose** that fetches news headlines from a remote API using **Retrofit**. This project implements the **MVVM** (Model-View-ViewModel) architecture and uses **Hilt** for dependency injection. News articles are displayed in a scrollable list, with the ability to paginate between different articles.

---

## Features
- **MVVM Architecture**: Clean separation of concerns between the UI, ViewModel, and data layers.
- **Jetpack Compose**: UI components are built using Jetpack Compose, the modern UI toolkit from Android.
- **Hilt for Dependency Injection**: Handles the dependency injection and lifecycle management of key components like the repository and data source.
- **Retrofit and OkHttp**: Used for making network requests to the news API.
- **StateFlow for Reactive Programming**: The app uses StateFlow to observe data changes and manage UI states like loading, success, and error.
- **LazyRow**: Displays news articles in a horizontally scrollable list.

---

## Project Structure

- `data`: Contains the data source, repository, and API service classes that handle the data layer.
  - `NewsDataSourceImpl`: Implementation for fetching news data from the API.
  - `ApiService`: Retrofit interface for making network requests.
  
- `di`: Contains the dependency injection setup using Hilt.
  - `AppModule`: Provides all dependencies such as `Retrofit`, `ApiService`, `NewsDataSource`, and `NewsRepository`.

- `ui`: Contains the composable UI screens.
  - `HomeScreen`: Displays a horizontally scrollable list of news articles using `LazyRow`.
  - `NewsRowComponent`: Displays individual news articles.
  - `Loader`: Shows a loading spinner while data is being fetched.
  - `EmptyStateComponent`: Displays an empty state when there are no articles available.

- `viewmodel`: Contains the `NewsViewModel` that handles the UI logic and manages data streams using StateFlow.

---

## Screenshots

[![App Screenshot](https://github.com/aliapochi/Insight/blob/master/screennew1.jpg)]
[![App Screenshot](https://github.com/aliapochi/Insight/blob/master/screennew2.jpg)]


---

## Getting Started

### Prerequisites

- Android Studio 4.1+
- Knowledge of Kotlin, Jetpack Compose, and MVVM architecture.

### Installation

1. Clone the repository:

```bash
git clone https://github.com/aliapochi/Insight.git
```

2. Open the project in **Android Studio**.

3. Build the project and sync Gradle.

4. Create an API key from your news API provider (e.g., NewsAPI) and add it to the project. (You can set this up as a constant in `AppConstants.kt`.)

### API Configuration

You need to configure the `AppConstants.APP_BASE_URL` and your `API_KEY` to fetch news headlines from your desired API.

```kotlin
object AppConstants {
    const val APP_BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "your_api_key_here"
}
```

### Running the App

After completing the setup, you can run the app on an Android device or emulator:

1. In Android Studio, click on the **Run** button or press `Shift + F10`.

2. The app should now be running, fetching and displaying news articles.

---

## Usage

- The app fetches the latest news headlines and displays them in a horizontally scrollable list.
- Each article includes its title, source, and published date.
- Users can scroll between articles using the LazyRow.

---

## Key Components

### Retrofit Integration

Retrofit is used to make network requests to the News API. It is configured in the `AppModule` as follows:

```kotlin
@Provides
@Singleton
fun providesRetrofit(): Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(httpLoggingInterceptor)
        readTimeout(60, TimeUnit.SECONDS)
    }.build()

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    return Retrofit.Builder()
        .baseUrl(AppConstants.APP_BASE_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
```

### Dependency Injection

The app uses Hilt for dependency injection. Here's a sample from `AppModule`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(newsDataSource: NewsDataSource): NewsRepository {
        return NewsRepository(newsDataSource)
    }
}
```

### ViewModel

The `NewsViewModel` is responsible for fetching the news data and exposing it via `StateFlow`:

```kotlin
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _news: MutableStateFlow<ResourceState<NewsResponse>> = MutableStateFlow(ResourceState.Loading())
    val news: StateFlow<ResourceState<NewsResponse>> = _news

    init {
        getNews(AppConstants.COUNTRY)
    }

    private fun getNews(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNewsHeadline(country).collectLatest { newsResponse ->
                _news.value = newsResponse
            }
        }
    }
}
```

---

## Libraries and Tools Used

- **Kotlin**: Programming language for building Android apps.
- **Jetpack Compose**: Android's modern UI toolkit.
- **Hilt**: Dependency injection library for Android.
- **Retrofit**: HTTP client for making API requests.
- **OkHttp**: Handles HTTP operations.
- **Moshi**: JSON library for parsing the API response.
- **StateFlow**: Reactive stream used to manage UI state.
- **LiveData**: Used for observing and responding to data changes.

---

## Future Enhancements

- Pagination for loading more articles dynamically.
- Implement caching for offline usage.
- Add more complex UI components (like search or filter features).

---

## Contributing

Feel free to fork the repository and submit pull requests for any new features, bug fixes, or performance improvements.
