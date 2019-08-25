package com.jd.blockchain.ledger.core.handles;

import com.jd.blockchain.ledger.BlockchainIdentity;
import com.jd.blockchain.ledger.LedgerPermission;
import com.jd.blockchain.ledger.UserRegisterOperation;
import com.jd.blockchain.ledger.core.LedgerDataset;
import com.jd.blockchain.ledger.core.LedgerService;
import com.jd.blockchain.ledger.core.MultiIdsPolicy;
import com.jd.blockchain.ledger.core.OperationHandleContext;
import com.jd.blockchain.ledger.core.SecurityContext;
import com.jd.blockchain.ledger.core.SecurityPolicy;
import com.jd.blockchain.ledger.core.TransactionRequestExtension;
import com.jd.blockchain.utils.Bytes;

public class UserRegisterOperationHandle extends AbstractLedgerOperationHandle<UserRegisterOperation> {
	public UserRegisterOperationHandle() {
		super(UserRegisterOperation.class);
	}

	@Override
	protected void doProcess(UserRegisterOperation op, LedgerDataset newBlockDataset,
			TransactionRequestExtension requestContext, LedgerDataset previousBlockDataset,
			OperationHandleContext handleContext, LedgerService ledgerService) {
		// 权限校验；
		SecurityPolicy securityPolicy = SecurityContext.getContextUsersPolicy();
		securityPolicy.checkEndpoints(LedgerPermission.REGISTER_USER, MultiIdsPolicy.AT_LEAST_ONE);

		// 操作账本；
		UserRegisterOperation userRegOp = (UserRegisterOperation) op;
		BlockchainIdentity bid = userRegOp.getUserID();

		Bytes userAddress = bid.getAddress();

		newBlockDataset.getUserAccountSet().register(userAddress, bid.getPubKey());
	}

}
