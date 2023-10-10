export interface AccountDetails {
  accountId:               string;
  balance:                 number;
  currentPage:             number;
  totalPages:              number;
  pageSize:                number;
  accountOperationDTOList: AccountOperation[];
}

export interface AccountOperation {
  id:            number;
  operationDate: Date;
  amount:        number;
  description:   string;
  type:          string;
}
