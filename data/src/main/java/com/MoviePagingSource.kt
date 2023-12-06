package com

//class MoviePagingSource @Inject constructor(
//    private val api: MovieApi,
//) : PagingSource<Int, MovieDto>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
//        return try {
//            val currentPage = params.key ?: 1
//            val movies = api.getTopRatedMoves(currentPage)
//            LoadResult.Page(
//                data = movies.mapFromListModel(),
//                prevKey = if (currentPage == 1) null else currentPage - 1,
//                nextKey = if (movies.results.isEmpty()) null else movies.page + 1
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
//        return state.anchorPosition
//    }
//
//}



