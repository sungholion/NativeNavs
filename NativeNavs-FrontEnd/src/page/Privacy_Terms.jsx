import React, { useState, useEffect } from "react";

const Privacy_Terms = () => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem("user")));
  }, []);

  if (!user || user?.isKorean) {
    return (
      <div className="PrivacyTerms">
        <h1>개인정보 처리방침</h1>

        <p>
          NativeNavs는(이하 ‘회사’라 한다)는 사용자의 개인정보 보호를
          중요시하며, 개인정보를 안전하게 처리하기 위해 다음과 같은 개인정보
          처리방침을 수립하고 있습니다. 이 방침은 사용자로부터 수집하는
          개인정보의 항목, 수집 목적, 처리 및 보관 방법, 제3자 제공 및 권리 행사
          방법에 대해 설명합니다.
        </p>

        <h2>1. 개인정보의 수집 및 이용 목적</h2>
        <p>회사는 다음과 같은 목적을 위해 개인정보를 수집 및 이용합니다:</p>
        <ul>
          <li>서비스 제공 및 관리: 회원 가입, 서비스 제공, 고객 지원</li>
          <li>이용자의 서비스 이용 기록 분석 및 맞춤형 서비스 제공</li>
          <li>법적 의무 준수: 법적 요구 사항 또는 규제에 따른 의무 이행</li>
          <li>
            마케팅 및 프로모션: 사용자 동의에 기반한 정보 제공 및 마케팅 활동
          </li>
        </ul>

        <h2>2. 수집하는 개인정보의 항목</h2>
        <p>회사는 다음과 같은 개인정보를 수집할 수 있습니다:</p>
        <ul>
          <li>기본정보: 이름, 연락처, 이메일 주소</li>
          <li>계정정보: 사용자 ID, 비밀번호</li>
          <li>이용정보: 접속 로그, IP 주소, 쿠키 정보</li>
        </ul>

        <h2>3. 개인정보의 보관 및 처리 방법</h2>
        <p>
          회사는 수집된 개인정보를 안전하게 보관하고 처리하기 위해 다음과 같은
          조치를 취합니다:
        </p>
        <ul>
          <li>데이터 암호화 및 접근 제어</li>
          <li>정기적인 보안 점검 및 업데이트</li>
          <li>개인정보 보호를 위한 내부 관리 규정 및 교육</li>
        </ul>

        <h2>4. 개인정보의 제3자 제공</h2>
        <p>
          회사는 법적 요구 또는 사용자의 동의를 받은 경우를 제외하고는
          개인정보를 제3자에게 제공하지 않습니다. 제3자에게 제공해야 하는 경우,
          제공되는 정보의 항목 및 제공 목적을 사전에 명확히 고지하고
          사용자로부터 동의를 받습니다.
        </p>

        <h2>5. 개인정보의 처리 위탁</h2>
        <p>
          회사는 서비스 제공을 위해 외부 업체에 개인정보 처리 업무를 위탁할 수
          있으며, 이 경우 위탁업체와의 계약을 통해 개인정보 보호를 위한 조치를
          취합니다. 위탁 업체의 명단과 연락처는 [웹사이트/앱]에 게시합니다.
        </p>

        <h2>6. 사용자 권리 및 행사 방법</h2>
        <p>
          사용자는 언제든지 개인정보 열람, 수정, 삭제 요청을 할 수 있으며,
          개인정보 처리에 대한 동의를 철회할 권리가 있습니다. 이러한 권리를
          행사하려면 고객센터 또는 [연락처]를 통해 요청하시면 됩니다.
        </p>

        <h2>7. 개인정보 보호책임자</h2>
        <p>
          회사는 개인정보 보호를 책임지는 담당자를 지정하여 개인정보 보호 관련
          업무를 수행합니다. 개인정보 보호책임자의 연락처는 다음과 같습니다:
        </p>
        <ul>
          <li>이름: [담당자명]</li>
          <li>이메일: [이메일 주소]</li>
          <li>전화번호: [전화번호]</li>
        </ul>

        <h2>8. 개인정보 처리방침의 변경</h2>
        <p>
          이 개인정보 처리방침은 법령의 변경, 회사의 정책 변화에 따라 수정될 수
          있으며, 수정된 내용은 [웹사이트/앱]에 공지합니다.
        </p>

        <h2>9. 문의처</h2>
        <p>개인정보 처리방침에 대한 문의는 아래의 연락처로 하실 수 있습니다:</p>
        <ul>
          <li>이메일: [이메일 주소]</li>
          <li>전화번호: [전화번호]</li>
          <li>주소: [회사 주소]</li>
        </ul>

        <p>이 개인정보 처리방침은 [날짜]부터 시행됩니다.</p>
      </div>
    );
  } else {
    return (
      <div className="PrivacyTerms">
        <h1>Privacy Policy</h1>

        <p>
          NativeNavs (hereinafter referred to as ‘the Company’) values your
          privacy and is committed to protecting your personal information. This
          Privacy Policy outlines how we collect, use, and protect your personal
          information, as well as how we handle your data, including third-party
          disclosures and your rights.
        </p>

        <h2>1. Purpose of Collecting and Using Personal Information</h2>
        <p>
          The Company collects and uses personal information for the following
          purposes:
        </p>
        <ul>
          <li>
            Service Provision and Management: Account creation, service
            provision, responding to inquiries, customer support
          </li>
          <li>
            Analysis of Service Usage Records and Provision of Customized
            Services
          </li>
          <li>
            Legal Compliance: Fulfillment of legal requirements or regulatory
            obligations
          </li>
          <li>
            Marketing and Promotion: Information provision and marketing
            activities based on user consent
          </li>
        </ul>

        <h2>2. Types of Personal Information Collected</h2>
        <p>
          The Company may collect the following types of personal information:
        </p>
        <ul>
          <li>Basic Information: Name, contact details, email address</li>
          <li>Account Information: User ID, password</li>
          <li>
            Transaction Information: Payment information, purchase history
          </li>
          <li>
            Usage Information: Access logs, IP address, cookie information
          </li>
        </ul>

        <h2>3. Methods of Handling and Storing Personal Information</h2>
        <p>
          The Company implements the following measures to securely handle and
          store personal information:
        </p>
        <ul>
          <li>Data encryption and access control</li>
          <li>Regular security checks and updates</li>
          <li>
            Internal management regulations and training for personal
            information protection
          </li>
        </ul>

        <h2>4. Disclosure of Personal Information to Third Parties</h2>
        <p>
          The Company does not disclose personal information to third parties
          except in cases required by law or with user consent. When disclosing
          information to third parties, the Company will provide prior notice
          regarding the types of information and purposes of disclosure and
          obtain user consent.
        </p>

        <h2>5. Outsourcing of Personal Information Processing</h2>
        <p>
          The Company may outsource personal information processing tasks to
          external vendors for service provision. In such cases, the Company
          ensures that appropriate measures are taken to protect personal
          information through contracts with the vendors. The list of outsourced
          vendors and their contact information will be posted on [Website/App].
        </p>

        <h2>6. User Rights and How to Exercise Them</h2>
        <p>
          Users have the right to access, correct, or delete their personal
          information and to withdraw consent to its processing at any time. To
          exercise these rights, users can contact customer service or [Contact
          Information].
        </p>

        <h2>7. Personal Information Protection Officer</h2>
        <p>
          The Company has designated a Personal Information Protection Officer
          to oversee personal information protection matters. Contact details
          for the Personal Information Protection Officer are as follows:
        </p>
        <ul>
          <li>Name: [Officer Name]</li>
          <li>Email: [Email Address]</li>
          <li>Phone Number: [Phone Number]</li>
        </ul>

        <h2>8. Changes to the Privacy Policy</h2>
        <p>
          This Privacy Policy may be updated due to changes in laws or company
          policies. Any updates will be posted on [Website/App].
        </p>

        <h2>9. Contact Information</h2>
        <p>
          For any questions regarding this Privacy Policy, please contact us at:
        </p>
        <ul>
          <li>Email: [Email Address]</li>
          <li>Phone Number: [Phone Number]</li>
          <li>Address: [Company Address]</li>
        </ul>

        <p>This Privacy Policy is effective as of [Date].</p>
      </div>
    );
  }
};

export default Privacy_Terms;
