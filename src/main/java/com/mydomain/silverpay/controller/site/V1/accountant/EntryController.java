package com.mydomain.silverpay.controller.site.V1.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.site.panel.entry.EntryStateChange;
import com.mydomain.silverpay.data.dto.site.panel.entry.EntryUpdateDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.FinancialDB.IEntryRepository;
import com.mydomain.silverpay.repository.MainDB.IBankCardRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import com.mydomain.silverpay.data.dto.site.panel.PaginationDto;
import com.mydomain.silverpay.data.model.financialDB.accountant.Entry;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.service.userService.UserService;
import com.mydomain.silverpay.service.userService.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EntryController {


    final IEntryRepository entryRepository;

    final IWalletRepository walletRepository;

    final IBankCardRepository bankCardRepository;

    final WalletService walletService;

    final UserService userService;

    EntryController(IEntryRepository entryRepository, IWalletRepository walletRepository, IBankCardRepository bankCardRepository, UserService userService, WalletService walletService) {

        this.entryRepository = entryRepository;
        this.walletRepository = walletRepository;
        this.bankCardRepository = bankCardRepository;
        this.userService = userService;
        this.walletService = walletService;
    }


    @GetMapping(Routes.Entry.get_entries)
    public ResponseEntity<Object> getEntries(PaginationDto paginationDto) {

        List<Entry> entries = entryRepository.findAll();//apply pagination and sorting


        return new ResponseEntity<>(entries, HttpStatus.OK);

    }

    @GetMapping(Routes.Entry.get_entry)
    public ResponseEntity<Object> getEntry(@PathVariable String entry_id) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);
        BankCard bankcardRepo = bankCardRepository.findById(entryRepo.getBankCardId()).orElse(null);
        Wallet walletRepo = walletRepository.findById(entryRepo.getWalletId()).orElse(null);

        entryRepo.setBankName(bankcardRepo.getBankName());
        entryRepo.setOwnerName(bankcardRepo.getOwnerName());
        entryRepo.setShaba(bankcardRepo.getShaba());
        entryRepo.setCardNumber(bankcardRepo.getCardNumber());
        entryRepo.setWalletName(walletRepo.getName());

        entryRepository.save(entryRepo);

        return ResponseEntity.ok().body(entryRepo);

    }


    @PutMapping(Routes.Entry.update_entry)
    public ResponseEntity<?> updateEntry(
            @PathVariable String entry_id, EntryUpdateDto updateDto) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);

        if (entryRepo != null) {

            entryRepo.setTextForUser(updateDto.getTextForUser());
            entryRepo.setBankTrackingCode(updateDto.getBankTrackingCode());
            entryRepository.save(entryRepo);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("no entry found for update");
        }

    }

    @PatchMapping(Routes.Entry.approve_entry)
    public ResponseEntity<?> approveEntry(
            @PathVariable String entry_id, EntryStateChange stateChange) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);


        if (entryRepo != null) {

            if (!entryRepo.isPayed() && !entryRepo.isReject()) {

                entryRepo.setApprove(stateChange.isApprove());
                entryRepository.save(entryRepo);
                return ResponseEntity.noContent().build();
            } else {

                return ResponseEntity.badRequest().body("entry approve not possible");

            }
        } else {
            return ResponseEntity.badRequest().body("no entry found for update");
        }

    }


    @PatchMapping(Routes.Entry.payment_entry)
    public ResponseEntity<?> paymentEntry(
            @PathVariable String entry_id, EntryStateChange stateChange) {

        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);


        if (entryRepo != null) {

            if (entryRepo.isApprove() && !entryRepo.isReject()) {


                if (stateChange.isPayed()) {

                    if (entryRepo.isPayed()) {
                        return ResponseEntity.noContent().build();
                    } else {

                        ReturnMessage result = walletService.entryDecreaseInventory(entryRepo.getPrice(), entryRepo.getWalletId());

                        if (!result.isStatus()) {
                            return ResponseEntity.badRequest().body("error in change of wallet inventory");
                        }
                    }
                } else {

                    if (!entryRepo.isPayed()) {
                        return ResponseEntity.noContent().build();
                    } else {
                        ReturnMessage result = walletService.entryIncreaseInventory(entryRepo.getPrice(), entryRepo.getWalletId());

                        if (!result.isStatus()) {
                            return ResponseEntity.badRequest().body("error in change of wallet inventory");
                        }
                    }

                }

                entryRepo.setPayed(stateChange.isPayed());
                entryRepository.save(entryRepo);
                return ResponseEntity.noContent().build();
            } else {

                return ResponseEntity.badRequest().body("change entry payment not possible");

            }
        } else {
            return ResponseEntity.badRequest().body("no entry found for update");
        }

    }


    @PatchMapping(Routes.Entry.reject_entry)
    public ResponseEntity<?> rejectEntry(
            @PathVariable String entry_id, EntryStateChange stateChange) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);


        if (entryRepo != null) {

            if (!entryRepo.isPayed()) {

//                decrease exit money
                if (stateChange.isReject()) {

                    if (entryRepo.isReject()) {
                        return ResponseEntity.noContent().build();
                    } else {
                        ReturnMessage result = walletService.entryDecreaseOnExitMoney(entryRepo.getPrice()
                                , entryRepo.getWalletId());

                        if (!result.isStatus()) {
                            return ResponseEntity.badRequest().body("error in change of wallet inventory");
                        }
                    }
                } else {

                    if (!entryRepo.isReject()) {
                        return ResponseEntity.noContent().build();
                    } else {
                        ReturnMessage result = walletService.entryIncreaseOnExitMoney(entryRepo.getPrice()
                                , entryRepo.getWalletId());

                        if (!result.isStatus()) {
                            return ResponseEntity.badRequest().body("error in change of wallet Exit Money,");
                        }
                    }


                }
//
                entryRepo.setReject(stateChange.isReject());
                entryRepository.save(entryRepo);
                return ResponseEntity.noContent().build();
            } else {

                return ResponseEntity.badRequest().body("change entry reject not possible");

            }
        } else {
            return ResponseEntity.badRequest().body("no entry found for update");
        }

    }


    @DeleteMapping(Routes.Entry.delete_entry)
    public ResponseEntity<?> deleteEntry(
            @PathVariable String entry_id) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);

        if (entryRepo != null) {
            entryRepository.delete(entryRepo);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.badRequest().body("no entry found");

        }


    }


    @GetMapping(Routes.Entry.get_approvedEntries)
    public ResponseEntity<Object> getApprovedEntries(PaginationDto paginationDto) {


        List<Entry> entryRepos = entryRepository.findAll()
                .stream().filter(Entry::isApprove).collect(Collectors.toList());

        return ResponseEntity.ok().body(entryRepos);

        //apply pagination
    }

    @GetMapping(Routes.Entry.get_bankcardEntries)
    public ResponseEntity<Object> getBankcardEntries(@PathVariable String bankcard_id, PaginationDto paginationDto) {


        List<Entry> entryRepos = entryRepository.findAll()
                .stream().filter(entry ->
                    entry.getBankCardId().equals(bankcard_id) && entry.isPayed()
                ).collect(Collectors.toList());

        return ResponseEntity.ok().body(entryRepos);

        //apply pagination

    }

    @GetMapping(Routes.Entry.get_walletEntries)
    public ResponseEntity<Object> getWalletEntries(@PathVariable String wallet_id, PaginationDto paginationDto) {


        List<Entry> walletEntriesRepo = entryRepository.findAll()
                .stream().filter(entry -> entry.getWalletId().equals(wallet_id)).collect(Collectors.toList());

        return ResponseEntity.ok().body(walletEntriesRepo);

        //apply pagination

    }


    @GetMapping(Routes.Entry.get_payedEntries)
    public ResponseEntity<Object> getPayedEntries(PaginationDto paginationDto) {


        List<Entry> entryRepos = entryRepository.findAll()
                .stream().filter(Entry::isPayed).collect(Collectors.toList());

        return ResponseEntity.ok().body(entryRepos);

        //apply pagination

    }

    @GetMapping(Routes.Entry.get_doneEntries)
    public ResponseEntity<Object> getDoneEntries(PaginationDto paginationDto) {


        List<Entry> entryRepos = entryRepository.findAll()
                .stream().filter(Entry::isApprove).collect(Collectors.toList());

        return ResponseEntity.ok().body(entryRepos);

        //apply pagination

    }

}
