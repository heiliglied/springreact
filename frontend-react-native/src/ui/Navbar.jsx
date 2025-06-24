import { Link } from "react-router";

const NavBar = () => {
    return (
        <nav className="w-screen fixed top-0 left-0 h-[55px] border-1 border-DEFAULT border-blue-600">
            <div className="mx-auto px-4 md:flex items-center">
                <div className="flex items-center justify-between md:w-auto w-full">
                    <div className="flex-1 items-center md:w-auto w-full py-4">
                        Example Site
                    </div>
                    {/* mobile menu icon */}
                    <div className="md:hidden flex items-center">
                        <button type="button" className="mobile-menu-button">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
                                <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25H12" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div className="hidden md:flex md:flex-row flex-col items-center justify-center md:space-x-1 pb-3 md:pb-0 navigation-menu container">
                    <Link to='/' className='py-2 px-3 block'>
                        Home
                    </Link>
                    <div className="relative">
                        <button type="button" className="dropdown-toggle py-2 px-3 hover:bg-sky-300 flex items-center gap-2 rounded">
                            <span className="pointer-events-none select-none">Services</span>
                            <svg className="w-3 h-3 pointer-events-none" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
                            </svg>
                        </button>
                        <div className="dropdown-menu absolute hidden bg-sky-300 text-white rounded-b-lg pb-2 w-48">
                            <a href="#" className="block px-6 py-2 hover:bg-sky-800">달력 샘플</a>
                        </div>
                    </div>
                </div>
                <div className="md:flex items-center md:w-auto ml-auto">
                    <div className="relative">
                        login
                        {/* 
                        <button type="button" className="dropdown-toggle py-2 px-3 hover:bg-sky-300 flex items-center gap-2 rounded">
                            <span className="pointer-events-none select-none">My</span>
                            <svg className="w-3 h-3 pointer-events-none" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
                            </svg>
                        </button>
                        */}
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default NavBar;