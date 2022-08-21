package com.mydomomain.silverpay.controller.site.V1.accountant;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.dto.site.panel.PaginationDto;
import com.mydomomain.silverpay.dto.site.panel.entry.EntryStateChange;
import com.mydomomain.silverpay.dto.site.panel.entry.EntryUpdateDto;
import com.mydomomain.silverpay.model.accountant.Entry;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.repository.financialDBRepository.IEntryRepository;
import com.mydomomain.silverpay.repository.mainRepository.IBankCardRepository;
import com.mydomomain.silverpay.repository.mainRepository.IWalletRepository;
import com.mydomomain.silverpay.service.userService.UserService;
import com.mydomomain.silverpay.service.userService.WalletService;
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

        if (entryRepo != null) {

            return ResponseEntity.ok().body(entryRepo);

        } else {
            return ResponseEntity.badRequest().body("no entry found");
        }

    }


    @PutMapping(Routes.Entry.update_entry)
    public ResponseEntity<?> updateEntry(
            @PathVariable String entry_id, EntryUpdateDto updateDto) {


        Entry entryRepo = entryRepository.findById(entry_id).orElse(null);

        if (entryRepo != null) {
            entryRepo.setTextForUser(updateDto.getTextForUser());
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
