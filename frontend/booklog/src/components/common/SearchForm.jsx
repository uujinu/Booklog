import React from 'react';
import styled from '@emotion/styled';
import CustomInput from './input';
import { FaSearch } from 'react-icons/fa';

const SearchForm = () => {
  const handleSubmit = e => {
    e.preventDefault();
    console.log('click!');
  };

  return (
    <SearchWrapper>
      <CustomInput
        style={{ width: `100%`, marginBottom: `0px`, border: `none` }}
      />
      <Btn type="submit" onClick={handleSubmit}>
        <FaSearch className="text-purple" size={32} />
      </Btn>
    </SearchWrapper>
  );
};

const SearchWrapper = styled.div`
  display: flex;
  width: 100%;
`;

const Btn = styled.button`
  color: white;
  margin-left: 10px;
`;

export default SearchForm;
