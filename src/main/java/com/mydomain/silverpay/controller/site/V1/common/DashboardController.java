package com.mydomain.silverpay.controller.site.V1.common;


import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.common.AdminDashboardDto;
import com.mydomain.silverpay.data.common.BlogDashboardDto;
import com.mydomain.silverpay.data.common.DaysReturnDto;
import com.mydomain.silverpay.data.common.UserDashboardDto;
import com.mydomain.silverpay.data.dto.site.panel.accountant.AccountantDashboardDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserBlogInfoDto;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.repository.FinancialDB.IEntryRepository;
import com.mydomain.silverpay.repository.FinancialDB.IFactorRepository;
import com.mydomain.silverpay.repository.MainDB.IBlogRepository;
import com.mydomain.silverpay.repository.MainDB.ITicketRepository;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class DashboardController {

    @Autowired
    ITicketRepository ticketRepository;

    @Autowired
    IFactorRepository factorRepository;

    @Autowired
    IEntryRepository entryRepository;

    @Autowired
    IWalletRepository walletRepository;

    @Autowired
    IBlogRepository blogRepository;

    @Autowired
    IUserRepository userRepository;


    @GetMapping(Routes.Dashboard.get_user_dashboard)
    public ResponseEntity<?> getUserDashboard(@PathVariable String user_id) {//only user role

        UserDashboardDto res = new UserDashboardDto();

        res.setUnClosedTicketCount(ticketRepository.countByUser_IdAndClosed(user_id, false));
        res.setClosedTicketCount(ticketRepository.countByUser_IdAndClosed(user_id, true));
        res.setLastFiveTickets(ticketRepository.findTop10ByUser_Id(user_id));
        res.setTotalInventory(walletRepository.findSumOfInventories(user_id));
        res.setTotalEnterMoney(walletRepository.findSumOfEnterMonies(user_id));

        DaysReturnDto daysInventoryReturnDto = new DaysReturnDto();

        daysInventoryReturnDto.setDay1(res.getTotalInventory() - entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now()));
        daysInventoryReturnDto.setDay2(res.getTotalInventory() - entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(1)));
        daysInventoryReturnDto.setDay3(res.getTotalInventory() - entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(2)));
        daysInventoryReturnDto.setDay4(res.getTotalInventory() - entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(3)));
        daysInventoryReturnDto.setDay5(res.getTotalInventory() - entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(4)));

        res.setInventory5Days(daysInventoryReturnDto);


        DaysReturnDto daysEnterMoneyReturnDto = new DaysReturnDto();

        daysEnterMoneyReturnDto.setDay1(factorRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now()));
        daysEnterMoneyReturnDto.setDay2(factorRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(1)));
        daysEnterMoneyReturnDto.setDay3(factorRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(2)));
        daysEnterMoneyReturnDto.setDay4(factorRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(3)));
        daysEnterMoneyReturnDto.setDay5(factorRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(4)));


        res.setEnterMoney5Days(daysEnterMoneyReturnDto);


        DaysReturnDto daysEntryReturnDto = new DaysReturnDto();

        daysEntryReturnDto.setDay1(entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now()));
        daysEntryReturnDto.setDay2(entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(1)));
        daysEntryReturnDto.setDay3(entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(2)));
        daysEntryReturnDto.setDay4(entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(3)));
        daysEntryReturnDto.setDay5(entryRepository.findSumOfPriceAtDate(user_id, LocalDateTime.now().minusDays(4)));

        res.setExitMoney5Days(daysEntryReturnDto);

//        res.setFactors10Days();
        res.setTotalExitMoney(walletRepository.findSumOfExitMonies(user_id));
        res.setLastTenFactors(factorRepository.findTop10ByUser_Id(user_id));//order by date created
        res.setLastTenEntries(entryRepository.findTop10ByUser_Id(user_id));//order by date created
        res.setTotalSuccessFactor(entryRepository.findSumOfPrice(user_id));
        res.setTotalSuccessEntry(entryRepository.findSumOfPrice(user_id));


        DaysReturnDto MonthFactorReturnDto = new DaysReturnDto();

        daysInventoryReturnDto.setDay1(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now()));
        daysInventoryReturnDto.setDay2(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(1)));
        daysInventoryReturnDto.setDay3(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(2)));
        daysInventoryReturnDto.setDay4(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(3)));
        daysInventoryReturnDto.setDay5(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(4)));
        daysInventoryReturnDto.setDay6(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(4)));
        daysInventoryReturnDto.setDay7(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(4)));
        daysInventoryReturnDto.setDay8(factorRepository.findSumOfEndPriceAtDate(user_id, LocalDateTime.now().minusMonths(4)));

        res.setFactors8Month(daysInventoryReturnDto);


//        res.setLastTenEntries();


        res.setTotalFactorIncome(factorRepository.findSumOfEndPriceWithKind(user_id, 1));
        res.setTotalEasyPayIncome(factorRepository.findSumOfEndPriceWithKind(user_id, 2));
        res.setTotalSupportIncome(factorRepository.findSumOfEndPriceWithKind(user_id, 3));
        res.setTotalIncInventoryIncome(factorRepository.findSumOfEndPriceWithKind(user_id, 4));
        res.setTotalSuccessFactor(factorRepository.findSumOfEndPrice(user_id));


        return ok(res);
    }


    @GetMapping(Routes.Dashboard.get_blog_dashboard)
    public ResponseEntity<?> getBlogDashboard(@PathVariable String user_id, HttpServletRequest request) {//only blog role

        BlogDashboardDto res = new BlogDashboardDto();

        if (request.isUserInRole("Admin")) {

            res.setTotalBlogCount((int) blogRepository.count());
            res.setApprovedBlogCount(blogRepository.countByStatus(true));
            res.setUnApprovedBlogCount(blogRepository.countByStatus(false));

            DaysReturnDto returnApproved5DaysDto = new DaysReturnDto();

            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(true, LocalDateTime.now()));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(true, LocalDateTime.now().minusDays(1)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(true, LocalDateTime.now().minusDays(2)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(true, LocalDateTime.now().minusDays(3)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(true, LocalDateTime.now().minusDays(4)));

            res.setTotalBlog5Days(returnApproved5DaysDto);


            DaysReturnDto returnUnApproved5DaysDto = new DaysReturnDto();

            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(false, LocalDateTime.now()));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(false, LocalDateTime.now().minusDays(1)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(false, LocalDateTime.now().minusDays(2)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(false, LocalDateTime.now().minusDays(3)));
            returnApproved5DaysDto.setDay1(blogRepository.countByStatusAndModifiedAt(false, LocalDateTime.now().minusDays(4)));


            res.setTotalBlog5Days(returnUnApproved5DaysDto);


            DaysReturnDto returnTotalBlogs5DaysDto = new DaysReturnDto();

            returnApproved5DaysDto.setDay1(blogRepository.countByModifiedAt(LocalDateTime.now()));
            returnApproved5DaysDto.setDay1(blogRepository.countByModifiedAt(LocalDateTime.now().minusDays(1)));
            returnApproved5DaysDto.setDay1(blogRepository.countByModifiedAt(LocalDateTime.now().minusDays(2)));
            returnApproved5DaysDto.setDay1(blogRepository.countByModifiedAt(LocalDateTime.now().minusDays(3)));
            returnApproved5DaysDto.setDay1(blogRepository.countByModifiedAt(LocalDateTime.now().minusDays(4)));


//            res.setLast7Blogs(blogRepository.findTop7());//with users and blogsGroup (map to blog return dto)


            List<User> userList = userRepository.findAll().stream().filter(user -> user.getRoles()

                            .stream().anyMatch(role -> role.getName().equals("blog")))
                    .collect(Collectors.toList());//get last 12 users with role blog and order by count


            List<UserBlogInfoDto> userBlogInfoDtos = new ArrayList<>();

            for (User user : userList) {
                userBlogInfoDtos.add(new UserBlogInfoDto(
                        user.getName(),
                        user.getBlogs().size(),
                        user.getBlogs().size(),//approve count ?
                        user.getBlogs().size()//unApprove count ?
                        )
                );
            }


//            res.setLast10UsersBlogInfo(); must be implement


            res.setTotalBlog5Days(returnTotalBlogs5DaysDto);


        } else {

        }


        return ok(res);
    }


    @GetMapping(Routes.Dashboard.get_accountant_dashboard)
    public ResponseEntity<?> getAccountantDashboard(HttpServletRequest request) {//only accountant role

        AccountantDashboardDto res = new AccountantDashboardDto();

//        res.setTotalEasyPay(); //set all



        return ok(res);
    }


    @GetMapping(Routes.Dashboard.get_admin_dashboard)
    public ResponseEntity<?> getAdminDashboard(HttpServletRequest request) {//only admin role

        AdminDashboardDto res = new AdminDashboardDto();




        return ok(res);
    }



}
