# NLResponseGenerator
a tool for http://getvene.com/ created during hackathon [HackPrague](http://hackprague.com/) 2017

This tool ingests a string message and produces the most relevant string responses, according to given NLP models. These models can be, e.g.
* sentence2vec
* [Skip-Thought Vectors](https://arxiv.org/abs/1506.06726)
* recurrent neural networks (GRU over GloVe)
* sentence representations from [fastText](https://github.com/facebookresearch/fastText)
* ...
