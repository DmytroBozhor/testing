package pl.piomin.services.grpc.account.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piomin.services.grpc.account.model.AccountProto;
import pl.piomin.services.grpc.account.model.AccountsServiceGrpc;
import pl.piomin.services.grpc.account.repository.AccountRepository;

import java.util.List;

@GrpcService
public class AccountsService extends AccountsServiceGrpc.AccountsServiceImplBase {

    @Autowired
    AccountRepository repository;

    @Override
    public void findByNumber(AccountProto.FindByNumberRequest request, StreamObserver<AccountProto.Account> responseObserver) {
        AccountProto.Account a = repository.findByNumber(request.getNumber());
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void findByCustomer(AccountProto.FindByCustomerRequest request, StreamObserver<AccountProto.Accounts> responseObserver) {
        List<AccountProto.Account> accounts = repository.findByCustomer(request.getCustomerId());
        AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(AccountProto.FindAllRequest request, StreamObserver<AccountProto.Accounts> responseObserver) {
        List<AccountProto.Account> accounts = repository.findAll();
        AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void addAccount(AccountProto.Account request, StreamObserver<AccountProto.Account> responseObserver) {
        AccountProto.Account a = repository.add(request.getCustomerId(), request.getNumber());
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }
}
