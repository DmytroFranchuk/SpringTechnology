package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "debitAccount.id", source = "senderAccountId")
    @Mapping(target = "creditAccount.id", source = "recipientAccountId")
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    @Mapping(target = "senderAccountId", source = "debitAccount.id")
    @Mapping(target = "recipientAccountId", source = "creditAccount.id")
    TransactionDto transactionToTransactionDto(Transaction transaction);

    @Mapping(target = "senderAccountId", source = "transaction.debitAccount.id")
    @Mapping(target = "senderAccountBalance", source = "transaction.debitAccount.balance")
    @Mapping(target = "recipientAccountId", source = "transaction.creditAccount.id")
    @Mapping(target = "recipientAccountBalance", source = "transaction.creditAccount.balance")
    TransactionResponseDto transactionToTransactionResponseDto(Transaction transaction);
}
