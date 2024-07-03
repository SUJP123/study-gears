import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Chatbot.css';

function Chatbot() {
    const [question, setQuestion] = useState('');
    const [answer, setAnswer] = useState('');
    const [videos, setVideos] = useState([]);

    const handleQuestionSubmit = (e) => {
        e.preventDefault();
        axios.post('https://study-gears-6cac3ab804b6.herokuapp.com/api/studybot/ask', { question })
            .then(response => {
                setAnswer(response.data.answer);
                setVideos(response.data.videos);
            })
            .catch(error => {
                console.error('Error fetching answer:', error);
            });
    };

    return (
        <div className="chatbot">
            <h3>Study Bot</h3>
            <form onSubmit={handleQuestionSubmit}>
                <input
                    type="text"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Ask a question"
                    required
                />
                <button type="submit">Ask</button>
            </form>
            {answer && (
                <div className="answer">
                    <h4>Answer:</h4>
                    <p>{answer}</p>
                </div>
            )}
            {videos.length > 0 && (
                <div className="videos">
                    <h4>Related Videos:</h4>
                    <ul>
                        {videos.map((video, index) => (
                            <li key={index}>
                                <a href={`https://www.youtube.com/watch?v=${video.id.videoId}`} target="_blank" rel="noopener noreferrer">
                                    <img src={video.snippet.thumbnails.default.url} alt={video.snippet.title} />
                                    <p>{video.snippet.title}</p>
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

export default Chatbot;
