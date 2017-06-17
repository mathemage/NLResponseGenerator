#!/usr/bin/env bash
~/fastText/fasttext cbow -input movie_text.txt -output movie-cbow
~/fastText/fasttext skipgram -input movie_text.txt -output movie-skipgram
