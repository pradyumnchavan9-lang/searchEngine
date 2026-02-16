import React, { useState } from "react";
import api from "../api/api";
import "./Search.css";

const Search = () => {
  const [input, setInput] = useState("");
  const [results, setResults] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/search", { query: input }, {
        headers: { "Content-Type": "application/json" }
      });
      setResults(response.data);
    } catch (error) {
      console.error("Search API error:", error);
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
        <button type="submit" className="search-button">Search</button>
      </form>

      <div className="results">
        {results.map((result, index) => (
          <div key={index} className="result-item">
            <a href={result.myDoc.url} target="_blank" rel="noopener noreferrer">
              {result.myDoc.url}
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Search;
