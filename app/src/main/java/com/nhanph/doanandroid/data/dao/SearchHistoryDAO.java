package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.nhanph.doanandroid.data.entities.SearchHistory;

import java.util.List;

@Dao
public interface SearchHistoryDAO {
    @Insert
    void insertSearchHistory(SearchHistory searchHistory);

    @Delete
    void deleteSearchHistory(SearchHistory searchHistory);

    @Query("DELETE FROM search_history")
    void deleteAllSearchHistory();

    @Query("SELECT * FROM search_history ORDER BY createdAt DESC LIMIT 5")
    List<SearchHistory> getLatestSearchHistory();

    @Query("DELETE FROM search_history WHERE keyword = :keyword")
    void deleteSearchHistoryByKeyword(String keyword);

    @Query("SELECT * FROM search_history WHERE keyword LIKE '%' || :keyword || '%'")
    List<SearchHistory> getSearchHistoryByKeyword(String keyword);

}
