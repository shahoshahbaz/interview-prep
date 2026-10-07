package practice.pagination.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse {
    private List<ProductResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}
