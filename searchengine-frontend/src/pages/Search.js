import React, { useState } from "react";
import api from "../api/api";
import "./Search.css";

const Search = () => {
  const [input, setInput] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await api.post(
        "/query",
        { query: input },
        {
          headers: { "Content-Type": "application/json" }
        }
      );

      setResults(response.data);
    } catch (error) {
      console.error("Search API error:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="search-container">
      <form className="search-form" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Search"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          className="search-input"
        />

        <button
          type="submit"
          className="search-button"
          disabled={loading}
        >
          {loading ? "Searching..." : "Search"}
        </button>
      </form>

      <div className="results">
        {!loading && results.length > 0 && (
          <p className="results-info">
            About {results.length} results
          </p>
        )}

        {loading && (
          <p className="loading-text">Searching...</p>
        )}

        {!loading && results.length === 0 && input && (
          <p className="no-results">No results found</p>
        )}

        {results.map((result, index) => (
          <div key={index} className="result-item">
            <a
              href={result.url}
              target="_blank"
              rel="noopener noreferrer"
              className="result-title"
            >
              {result.title}
            </a>

            <p className="result-url">
              {new URL(result.url).hostname.replace("www.", "")}
            </p>

            <p className="result-snippet">
              {result.summary?.length > 160
                ? result.summary.slice(0, 160) + "..."
                : result.summary}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Search;
