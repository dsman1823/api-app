package com.test.log.dto;

import lombok.Data;

@Data
public class Pageable {
        private Integer pageNumber = 0;
        private Integer pageSize = 20;
}
