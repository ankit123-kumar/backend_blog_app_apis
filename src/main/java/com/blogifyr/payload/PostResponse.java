package com.blogifyr.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {


	private List<PostDto> content;
	
	private int pageSize;
	private int pageNumber;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
