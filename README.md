# Aspect-Oriented-Programming-in-Spring-framework

Implementing the retry and authorization concerns for a blog service through Aspect Oriented Programming (AOP).  
The blog service allows access to and sharing of blogs. 
One can share his blog with others, and can share  with others the blogs he is shared with too. 

Following conditions are implemented in the project

Once can share his blog with anybody.
One can only read blogs that are shared with him, or his own blog. In any other case, an AccessDeniedExeption is thrown.
If Alice shares her blog with Bob, Bob can further share Alice’s blog with Carl. If Alice attempts to share Bob’s blog with Carl while Bob’s blog is not shared with Alice in the first place, Alice gets an AccessDeniedExeption.
One can only unshare his own blog. When unsharing a blog with Bob that the blog is not shared by any means with Bob in the first place, the operation throws an AccessDeniedExeption. 
Both sharing and unsharing of Alice’s blog with Alice have no effect; i.e. Alice always has access to her own blog, and can share and unshare with herself without encountering any exception, even these operations do not take any effect.
