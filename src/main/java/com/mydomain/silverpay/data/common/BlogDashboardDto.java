package com.mydomain.silverpay.data.common;

import com.mydomain.silverpay.data.dto.site.panel.blog.BlogReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserBlogInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlogDashboardDto {

    private int totalBlogCount;
    private int approvedBlogCount;
    private int unApprovedBlogCount;

    private DaysReturnDto totalBlog5Days;
    private DaysReturnDto unApprovedBlog5Days;
    private DaysReturnDto approvedBlog5Days;

    private List<BlogReturnDto> last7Blogs;

    private List<UserBlogInfoDto> last10UsersBlogInfo;

}
